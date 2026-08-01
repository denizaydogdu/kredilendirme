# Ders 7 — Git, Branch Stratejisi ve Commit Disiplini

## PR = bankadaki "dual control" (dört göz)
Önemli işlem tek kişiyle yapılmaz; kod da öyle: feature dalı → PR → **başkası onaylar** → merge.

## Dal yapımız
```
main     ← canlı, doğrudan commit YASAK
develop  ← geliştirme hattı
feature/gunX-konu ← her iş parçası
```

## .gitignore — İLK commit'ten önce
`target/` (üretilebilir), `.idea/` (kişisel), **`.env`/`*.key` (ŞİFRE — commit'lenirse
tarihçede kalır, silmek kurtarmaz; şifre yanmıştır, değiştirilir).**

## Conventional Commits
```
feat(limit): add optimistic locking to credit limit
fix(repayment): write rounding difference to the final installment
test/docs/refactor/chore
```
Mesaj "ne"yi değil "neden"i anlatır.

## 🎯 Mülakat
**S: Merge vs rebase?**
C: Merge iki geçmişi birleştirir, merge commit oluşur, geçmiş korunur. Rebase commit'leri hedef
dalın ucuna yeniden yazar; geçmiş düz olur ama kimlikler değişir — bu yüzden **paylaşılan dalda
rebase yapılmaz**. Kendi feature dalımı güncellerken rebase, teslimde merge.

**S: revert vs reset?**
C: `revert` yeni bir ters commit üretir — paylaşılan dalda güvenli. `reset` geçmişi geri sarar
(`--soft` commit'i geri al, `--mixed` +stage'i, `--hard` +dosyaları) — sadece lokalde.
