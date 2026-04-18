#!/usr/bin/env python3
"""
Scan Markdown under DOCS/, LANG/, and repo-root CHANGELOG/PRESENTATION/README
for legacy AppDimens-style patterns inside ```kotlin and ```dart fences only.

Usage (from repo root):
  python3 scripts/scan_documentation_code_fences.py
  python3 scripts/scan_documentation_code_fences.py --write

Default: print summary to stdout. With --write, regenerates DOCS/DOCUMENTATION_REVIEW_CHECKLIST.md
"""
from __future__ import annotations

import argparse
import re
import sys
from collections import defaultdict
from pathlib import Path

REPO = Path(__file__).resolve().parent.parent

KOTLIN_PATTERNS: list[tuple[str, str]] = [
    (r"\.balanced\(\)\.d[ps]", "legacy .balanced().dp/.sp"),
    (r"\.defaultDp\b|\.defaultSp\b", "defaultDp/defaultSp"),
    (r"\.percentageDp", "percentageDp"),
    (r"\.fxdp\b|\.dydp\b|\.fxsp\b|\.dysp\b", "fxdp/dydp family"),
    (r"AppDimens\.from\(", "AppDimens.from (unified smart)"),
    (r"defaultScaling\(\)\.d[ps]", "defaultScaling().dp"),
    (r"\.smart\(\)\.forElement", "Android smart() chain"),
]
DART_PATTERNS: list[tuple[str, str]] = [
    (r"AppDimens\.balanced\(", "AppDimens.balanced"),
    (r"AppDimens\.defaultScaling\(", "AppDimens.defaultScaling static"),
    (r"AppDimens\.smart\(", "AppDimens.smart static"),
    (r"AppDimens\.fluid\(\s*\d+\s*,", "AppDimens.fluid(min, max)"),
    (r"AppDimens\.percentage\(\d", "AppDimens.percentage digit"),
    (r"\b\d+\.0\.balanced\(\)|(?<![A-Za-z.])\d+\.balanced\(\)", "num.balanced()"),
]


def collect_markdown_files() -> list[Path]:
    out: list[Path] = list((REPO / "DOCS").glob("*.md"))
    out += list((REPO / "LANG").rglob("*.md"))
    for name in ("README.md", "PRESENTATION.md", "CHANGELOG.md"):
        p = REPO / name
        if p.is_file():
            out.append(p)
    return sorted(set(out), key=lambda p: str(p))


def extract_fences(text: str, lang: str) -> list[str]:
    pat = re.compile(
        r"^```\s*" + re.escape(lang) + r"\s*\n(.*?)^```\s*$", re.M | re.S
    )
    return [m.group(1) for m in pat.finditer(text)]


def scan_file(path: Path) -> tuple[list[tuple[str, str]], list[tuple[str, str]]]:
    text = path.read_text(encoding="utf-8", errors="replace")
    kotlin_hits: list[tuple[str, str]] = []
    dart_hits: list[tuple[str, str]] = []
    for block in extract_fences(text, "kotlin"):
        for line in block.splitlines():
            if re.search(r"//.*[Dd]eprecated", line):
                continue
            for pat, name in KOTLIN_PATTERNS:
                if re.search(pat, line):
                    kotlin_hits.append((name, line.strip()[:220]))
    for block in extract_fences(text, "dart"):
        for line in block.splitlines():
            for pat, name in DART_PATTERNS:
                if re.search(pat, line):
                    dart_hits.append((name, line.strip()[:220]))
    return kotlin_hits, dart_hits


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--write",
        action="store_true",
        help="Write DOCS/DOCUMENTATION_REVIEW_CHECKLIST.md",
    )
    args = parser.parse_args()

    md_files = collect_markdown_files()
    per_file: dict[str, dict[str, list]] = {}
    for path in md_files:
        rel = str(path.relative_to(REPO))
        k, d = scan_file(path)
        per_file[rel] = {"kotlin": k, "dart": d}

    hits_any = [r for r, v in per_file.items() if v["kotlin"] or v["dart"]]
    clean = [r for r, v in per_file.items() if not v["kotlin"] and not v["dart"]]

    lines: list[str] = []
    lines.append("# Checklist de revisão — padrões em blocos de código")
    lines.append("")
    lines.append(
        "Análise automática: apenas dentro de cercas `` ```kotlin`` e `` ```dart`` "
        "em `DOCS/`, `LANG/`, `README.md`, `PRESENTATION.md`, `CHANGELOG.md`."
    )
    lines.append(
        "Linhas Kotlin com comentário `// Deprecated` (migração intencional) são ignoradas."
    )
    lines.append("")
    lines.append("| Ficheiro | Kotlin | Dart | Rever manualmente? |")
    lines.append("|----------|--------|------|---------------------|")
    for rel in sorted(per_file.keys()):
        k = len(per_file[rel]["kotlin"])
        d = len(per_file[rel]["dart"])
        flag = "Sim — ver detalhe" if (k + d) else "Não (nestes padrões)"
        lines.append(f"| [{rel}]({rel}) | {k} | {d} | {flag} |")
    lines.append("")
    lines.append("## Detalhe — ficheiros com ocorrências")
    lines.append("")
    if not hits_any:
        lines.append(
            "*Nenhum padrão na lista foi encontrado em blocos Kotlin/Dart "
            "(após filtrar comentários Deprecated).*"
        )
        lines.append("")
    else:
        for rel in sorted(hits_any):
            lines.append(f"### `{rel}`")
            lines.append("")
            for name, snip in per_file[rel]["kotlin"]:
                lines.append(f"- **Kotlin** — {name}: `{snip}`")
            for name, snip in per_file[rel]["dart"]:
                lines.append(f"- **Dart** — {name}: `{snip}`")
            lines.append("")
    lines.append("## Estatísticas")
    lines.append("")
    lines.append(f"- Ficheiros `.md` analisados: **{len(per_file)}**")
    lines.append(f"- Com pelo menos um hit: **{len(hits_any)}**")
    lines.append(f"- Sem hits nestes padrões: **{len(clean)}**")
    lines.append("")
    lines.append("## Padrões Kotlin")
    lines.append("")
    for pat, name in KOTLIN_PATTERNS:
        lines.append(f"- `{pat}` — {name}")
    lines.append("")
    lines.append("## Padrões Dart")
    lines.append("")
    for pat, name in DART_PATTERNS:
        lines.append(f"- `{pat}` — {name}")
    lines.append("")
    lines.append("## Como repetir")
    lines.append("")
    lines.append("```bash")
    lines.append("python3 scripts/scan_documentation_code_fences.py --write")
    lines.append("```")
    lines.append("")
    lines.append("---")
    lines.append("")
    lines.append(
        "*Swift, TypeScript, XML e texto corrido não entram nesta varredura.*"
    )

    body = "\n".join(lines)
    out_path = REPO / "DOCS" / "DOCUMENTATION_REVIEW_CHECKLIST.md"

    print(f"Files scanned: {len(per_file)}")
    print(f"Files with hits: {len(hits_any)}")
    if hits_any:
        for r in hits_any:
            print(
                " ",
                r,
                len(per_file[r]["kotlin"]),
                len(per_file[r]["dart"]),
            )

    if args.write:
        out_path.write_text(body, encoding="utf-8")
        print(f"Wrote {out_path.relative_to(REPO)}")
    elif hits_any:
        print("\nRun with --write to refresh the markdown report.")

    return 1 if hits_any else 0


if __name__ == "__main__":
    sys.exit(main())
