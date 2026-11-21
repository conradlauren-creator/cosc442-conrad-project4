# AGENTS.md

- Build with Java 25 and Maven through `.\mvnw.cmd` in Windows PowerShell.
- Run `./mvnw test` or `.\mvnw.cmd test` before work is complete. Run PIT and SpotBugs with `pitest:mutationCoverage` and `spotbugs:spotbugs` when the change is relevant to mutation or static-analysis quality.
- Preserve existing behavior unless a change is explicitly requested.
- Do not weaken, remove, skip, or disable tests to make the build pass.
- Identify every production-code change clearly in the final summary for human review.
- Do not push or merge changes automatically.
