# Ders 2 — Kredi Nedir?

## Üç bileşen
| Bileşen | Kodda | Örnek |
|---|---|---|
| Anapara | `principal` | 500.000 TL |
| Faiz | `interest` | paranın kirası |
| Vade | `maturity` / `term` | 24 ay |

## Kredinin yaşam döngüsü (EZBERLE — projenin iskeleti)
```
1. BAŞVURU        → LoanApplication
2. DEĞERLENDİRME  → mali analiz + Collateral (teminat)
3. KARAR/TAHSİS   → CreditDecision + CreditLimit
4. KULLANDIRIM    → Disbursement          ⭐ para burada çıkar
5. ÖDEME PLANI    → RepaymentSchedule + Installment
6. GERİ ÖDEME     → taksitler
7. KAPANIŞ        (ödenmezse → GECİKME → TAKİP/NPL)
```

## ⭐ LİMİT TAHSİS ≠ KULLANDIRIM
| | Tahsis (allocation) | Kullandırım (disbursement) |
|---|---|---|
| Anlam | "1M TL'ye kadar izin" | "300K hesabına geçti" |
| Para çıktı mı? | HAYIR | EVET |
| Analoji | kredi kartı limiti | kartla harcama |

Tahsis 1.000.000 − Kullanılan 300.000 = Kullanılabilir 700.000

## NAKİT vs GAYRİNAKDİ
| | Nakit | Gayrinakdi |
|---|---|---|
| Para çıkar mı? | Evet | Hayır — banka GARANTİ verir |
| Örnek | spot, rotatif, taksitli | teminat mektubu, harici garanti |
| Kazanç | faiz | komisyon |
| Risk | doğrudan | şarta bağlı (contingent) |

## 🎯 Mülakat
**S: Kredi kullandırım süreci hakkında ne biliyorsunuz?**
C: Başvuru → mali analiz + teminat → onay → limit tahsisi (para çıkışı değildir) →
kullandırım: para fiilen hesaba geçer, limitin kullanılan kısmı artar, ödeme planı üretilir.
Kullandırım atomiktir: limit güncellemesi ile kayıt ya birlikte olur ya hiç olmaz.
