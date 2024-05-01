import 'package:intl/intl.dart';

class ReportInforRequest {
  final String? provine;
  final String? district;
  final String? ward;
  final DateTime? month;
  final String? search;

  ReportInforRequest({
    this.provine,
    this.district,
    this.ward,
    this.month,
    this.search,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'provine': provine,
      'district': district,
      'ward': ward,
      'month': month != null ? DateFormat('yyyy-MM-dd').format(month!) : null,
      'search': search,
    };
  }
}
