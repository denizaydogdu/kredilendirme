# Bankacılık Sözlüğü (TR ↔ EN)

Kod isimlendirmesinin kaynağı. Mülakatta iki dilde terim bilmek artı puandır.

## Temel kavramlar
| Türkçe | Kodda | Açıklama |
|---|---|---|
| Müşteri | `Customer` | Bankayla ilişkisi olan gerçek/tüzel kişi |
| Kredi | `Loan` | Bugün verilen, gelecekte anapara+faiz dönen finansman |
| Anapara | `principal` | Verilen asıl tutar |
| Faiz | `interest` | Paranın kirası, bankanın kazancı |
| Vade | `maturity` / `term` | Geri ödeme süresi |
| Risk | `exposure` | Müşterinin bankaya toplam borcu |
| Teminat | `Collateral` | Kredinin güvencesi (ipotek, kefalet, rehin...) |
| Teminat oranı | `collateralRatio` | Teminat değeri / kredi tutarı |

## Süreç
| Türkçe | Kodda | Açıklama |
|---|---|---|
| Kredi başvurusu | `LoanApplication` | Sürecin 1. adımı |
| Kredi kararı | `CreditDecision` | Onay/red/manuel inceleme |
| Limit tahsis | `CreditLimit` (allocation) | "Şu tutara kadar izin" — para çıkışı DEĞİL |
| **Kullandırım** | **`Disbursement`** | Paranın fiilen hesaba geçmesi |
| Ödeme planı | `RepaymentSchedule` | Taksitlerin tamamı |
| Taksit | `Installment` | Tek geri ödeme satırı |
| Geri ödeme | `Repayment` | Müşterinin ödemesi |
| Gecikme | `overdue` | Vadesinde ödenmeyen |
| Takip / NPL | `non-performing loan` | Donuk alacak |

## Ürünler
| Türkçe | Kodda | Açıklama |
|---|---|---|
| Nakit kredi | `cash loan` | Para çıkışı olan |
| Gayrinakdi kredi | `non-cash loan` | Garanti/taahhüt; komisyon kazandırır |
| Spot kredi | `SpotLoan` | Faiz kullandırımda sabit, tek ödeme vade sonunda |
| Rotatif kredi | `RevolvingLoan` | Limit içinde çek-öde; ödedikçe limit yenilenir |
| Taksitli ticari kredi | `InstallmentLoan` | Vadelere yayılmış geri ödeme |
| Teminat mektubu | `LetterOfGuarantee` | "İş yapılmazsa ben öderim" garantisi |
| Harici garanti | `ExternalGuarantee` | Yurtdışı muhataba verilen garanti |
| Kontrgaranti | `CounterGuarantee` | Başka bankanın garantisini destekleyen garanti |
| Referans/Niyet mektubu | `ReferenceLetter` / `LetterOfIntent` | Taahhüt içermeyen mektuplar |
| Kabul/Aval | `AcceptanceCredit` | Poliçe ödemesinin banka garantisi |
| İGE kefaleti | `IGE surety` | İhracatı Geliştirme A.Ş. kefaleti |
| İhracat taahhüdü | `ExportCommitment` | 24 ayda ihracat gerçekleştirme sözü (vergi istisnası) |
| Prefinansman | `pre-financing` | Sevk öncesi ihracat finansmanı |

## Faiz & vergi
| Türkçe | Kodda | Açıklama |
|---|---|---|
| Sabit faiz | `fixed rate` | Kullandırımda çakılır; riski banka taşır |
| Değişken faiz | `floating rate` | Fonlama maliyetiyle değişir; riski müşteri taşır |
| Annüite / eşit taksit | `annuity` | T = A·i·(1+i)ⁿ/((1+i)ⁿ−1) |
| Fonlama maliyeti | `cost of funding` | Bankanın parayı bulma maliyeti |
| BSMV | `BSMV (banking tax)` | Faiz üzerinden %5 (ticari) |
| KKDF | `KKDF (fund levy)` | Ticari kredide 0 |
| Gün esası | `DayCountConvention` | TL: ACT/365, döviz: ACT/360 |
| Erken kapatma | `prepayment` | Kalan anapara ödenir (kalan taksit toplamı DEĞİL) |
