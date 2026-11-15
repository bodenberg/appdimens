# Working on the AppDimens family

- **Always validate CI/CD after every push**: list the GitHub Actions runs of each repository you pushed
  (hub and submodules), read the logs of every failed job, fix the cause (never skip or disable a test),
  push again and repeat until every workflow is green. Workflows that only run on `main` can be started on a
  branch with `workflow_dispatch`.
- `appdimens-dynamic` is the reference implementation. When its math changes, run
  `python3 tools/parity/generate.py` there and copy `tools/parity/parity-vectors.json` into the ports'
  `tests/golden.json` (py, gtk, lvgl, qt, games native + godot), then re-run their tests.
- Validate workflow YAML before pushing: `python3 -c "import yaml,sys; yaml.safe_load(open(sys.argv[1]))" <file>`.
