#!/usr/bin/env python3
"""Writes the shared "AppDimens family" block into the README of every library (between markers).

  python3 tools/family_block.py /path/with/clones          # write
  python3 tools/family_block.py /path/with/clones --check  # fail when a README is stale
"""
import os
import re
import sys

BEGIN = "<!-- appdimens-family:begin (maintained in bodenberg/appdimens tools/family_block.py) -->"
END = "<!-- appdimens-family:end -->"
H = "https://github.com/bodenberg/"
ROWS = [
    ("Android — Jetpack Compose, Views, Java", "appdimens-dynamic", "`16.sdp` · `16.sdp(context)`"),
    ("Android — XML resources", "appdimens-sdps", "`@dimen/_16sdp`"),
    ("Android — XML text sizes", "appdimens-ssps", "`@dimen/_16ssp`"),
    ("Kotlin / Compose Multiplatform", "appdimens-kmp", "`16.sdp`"),
    ("iOS · macOS — SwiftUI, UIKit, AppKit, Metal", "appdimens-ios", "`16.sdp`"),
    ("Flutter", "appdimens-flutter", "`16.sdp`"),
    ("React Native / Expo", "appdimens-react-native", "`sdp(16)`"),
    ("Web — HTML/CSS, React, Vue, Svelte, Angular", "appdimens-web", "`sdp(16)` · `16sdp`"),
    (".NET — MAUI, WPF, WinForms, WinUI, Android", "appdimens-net-binding", "`16.Sdp()` · `{sdp:16}`"),
    ("Qt 5/6 — QML, Widgets", "appdimens-qt", "`AppDimens.sdp(16)` · `16_sdp`"),
    ("GTK 3/4 and portable C99", "appdimens-gtk", "`16sdp` (CSS) · `ad_sdp(&m, 16)`"),
    ("LVGL 8/9 — MCUs, embedded Linux", "appdimens-lvgl", "`lv_ad_sdp(16)`"),
    ("Roku — BrightScript / SceneGraph", "appdimens-roku", "`sdp(16)` · `ad.sdp(16)`"),
    ("Python — Tkinter, Qt, Kivy, pygame, Blender", "appdimens-py", "`ad.sdp(16)`"),
    ("Games — Unity, Unreal, Godot, C/C++, .NET, Android", "appdimens-games",
     "`64f.ASdp()` · `AppDimens.asdp(64)` · `appdimens::asdp(64)`"),
]


def block(repo):
    lines = [BEGIN, "## 🌍 AppDimens family", "",
             "Same token names and the same math on every platform — [hub](%sappdimens) · "
             "[AI agent skills](%sappdimens-skills) · [custom strategies](%sappdimens/blob/main/DOCS/CUSTOM.md) "
             "(one JSON for every platform)." % (H, H, H), "",
             "| Platform | Library | Scaled `16` |", "|---|---|---|"]
    for plat, lib, call in ROWS:
        name = "**%s** (this repository)" % lib if lib == repo else "[%s](%s%s)" % (lib, H, lib)
        lines.append("| %s | %s | %s |" % (plat, name, call))
    lines += ["", END]
    return "\n".join(lines)


def apply(text, repo):
    b = block(repo)
    if BEGIN.split(" (")[0] in text:
        return re.sub(r"<!-- appdimens-family:begin.*?<!-- appdimens-family:end -->", lambda _: b, text, flags=re.S)
    for heading in ("## 🇧🇷", "## Resumo em português", "## Português (resumo)", "## License", "## Author",
                    "## 🤝 Contributing", "## Development", "## Publishing"):
        m = re.search(r"^" + re.escape(heading), text, re.M)
        if m:
            return text[:m.start()] + b + "\n\n" + text[m.start():]
    return text.rstrip("\n") + "\n\n" + b + "\n"


root = sys.argv[1]
check = "--check" in sys.argv
stale = []
for _, repo, _ in ROWS:
    p = os.path.join(root, repo, "README.md")
    if not os.path.exists(p):
        continue
    old = open(p).read()
    new = apply(old, repo)
    if new != old:
        if check:
            stale.append(repo)
        else:
            open(p, "w").write(new)
            print("updated", repo)
if stale:
    sys.exit("stale family block: " + ", ".join(stale))
