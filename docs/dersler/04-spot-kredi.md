# Ders 4 — Spot Kredi

## Tanım
Faiz oranı **kullandırım anında sabitlenir**; anapara + faiz **vade sonunda tek seferde** ödenir.
Ara taksit yoktur. Kısa vadeli, parası belli tarihte girecek firmalar için (ör. ihracat bedeli bekleyen).

## Hesap
```
FAİZ = Anapara × Yıllık Oran × (Gün / 365)

1.000.000 TL, 90 gün, %45:
Faiz  = 1.000.000 × 0,45 × 90/365 = 110.958,90
BSMV  = faizin %5'i              =   5.547,95
Vade sonu toplam                 = 1.116.506,85
```

## Day count convention (senior detayı)
| Ürün | Esas |
|---|---|
| TL krediler | ACT/365 |
| Döviz krediler | ACT/360 |

365 sabit değil, **ürüne bağlı iş kuralı** → kodda `DayCountConvention` enum'u.
Oranlar (faiz, BSMV) ise **geçerlilik tarihli olarak veritabanında** tutulur — geçmiş işlem
yeniden hesaplanırken o günkü oran gerekir (temporal data).

## Erken kapatma neden cezalı?
Banka o parayı kendisi de vadeli borçlandı (fonlama maliyeti). 90 günlük fonlamanın
30. günde kapanması bankaya maliyet bırakır.

## 🎯 Mülakat
**S: Spot ile taksitli kredi farkı?**
C: Spotta faiz kullandırımda sabitlenir, tek ödeme vade sonundadır; taksitlide geri ödeme
vadelere yayılır ve her taksit anapara+faiz içerir. Teknikte spot tek satırlık ödeme planı
üretir, taksitli n satırlık.
