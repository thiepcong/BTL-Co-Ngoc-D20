class ReportInforRequest {
  final String? provine;
  final String? district;
  final String? ward;
  final DateTime? month;
  final String? invoiceStatus;
  final String? search;
  final int? page;
  final int? size;

  ReportInforRequest({
    this.provine,
    this.district,
    this.ward,
    this.month,
    this.invoiceStatus,
    this.search,
    this.page,
    this.size,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'provine': provine,
      'district': district,
      'ward': ward,
      'month': month?.toString(),
      'invoiceStatus': invoiceStatus,
      'search': search,
      'page': page,
      'size': size,
    };
  }
}
