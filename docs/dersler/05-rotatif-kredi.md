# Ders 5 — Rotatif Kredi

## Tanım
Limit dahilinde **istediğin zaman çek, istediğin zaman öde**; ödedikçe limit yeniden açılır
(revolving). Ticari kredi kartı mantığı. Nakit akışı düzensiz firmalar için.

## Faiz: günlük bakiye üzerinden
```
Limit 1.000.000, %45:
 1-14 Oca : bakiye 300.000 → 300.000×0,45×14/365 =  5.178,08
15-31 Oca : bakiye 500.000 → 500.000×0,45×17/365 = 10.479,45
 1-28 Şub : bakiye 300.000 → 300.000×0,45×28/365 = 10.356,16
Dönem faizi (3 ayda bir tahsil)              = 26.013,69
```
Hiç çekmediysen faiz ödemezsin — faiz limite değil **kullanılan bakiyeye** işler.

## Spot ile fark
| | Spot | Rotatif |
|---|---|---|
| Kullandırım | tek sefer | defalarca |
| Geri ödeme | vade sonu | istediğin zaman |
| Faiz | sabit anapara | günlük değişen bakiye |
| Limit | tek kullanımlık | ödedikçe yenilenir |

## ⭐ Teknik sonuç: LOST UPDATE
Limit paylaşılan kaynaktır. İki şube aynı anda "kalan 700K" görür, ikisi de kullandırır →
1.100.000 kullandırıldı, limit 1.000.000. Çözüm:
- **Optimistic locking** (`@Version`) — bizim seçim
- Pessimistic locking (`SELECT ... FOR UPDATE`)

```java
@Entity class CreditLimit {
    BigDecimal totalLimit; BigDecimal usedAmount; BigDecimal reservedAmount;
    @Version Long version;
    BigDecimal availableAmount() { return totalLimit.subtract(usedAmount).subtract(reservedAmount); }
}
```
`reservedAmount` → saga'nın "limit rezerve et" adımı.

## 🎯 Mülakat
**S: Rotatif kredinin çalışma mantığı?**
C: Limit tahsis edilir; müşteri limit dahilinde kullandırır ve öder, ödenen tutar limiti yeniden
açar. Faiz günlük kullanılan bakiye üzerinden hesaplanır, dönemsel tahsil edilir. Limit paylaşılan
kaynak olduğu için eşzamanlı kullandırımda lost update riski vardır; `@Version` ile optimistic
locking uygularım.
