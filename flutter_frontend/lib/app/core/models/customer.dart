class Customer {
  final int customerId;
  final String? customerName;
  final String? customerPhone;
  final String? customerEmail;
  final String? provine;
  final String? district;
  final String? ward;
  final int? waterUsageNumber;
  final int? newWaterUsageIndex;
  final int? oldWaterUsageIndex;
  final DateTime? startTime;
  final DateTime? endTime;
  final String? status;
  final int? moneyNumber;
  final int? debtMoneyNumber;

  Customer({
    required this.customerId,
    this.customerName,
    this.customerPhone,
    this.customerEmail,
    this.provine,
    this.district,
    this.ward,
    this.waterUsageNumber,
    this.newWaterUsageIndex,
    this.oldWaterUsageIndex,
    this.startTime,
    this.endTime,
    this.status,
    this.moneyNumber,
    this.debtMoneyNumber,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'customerId': customerId,
      'customerName': customerName,
      'customerPhone': customerPhone,
      'customerEmail': customerEmail,
      'provine': provine,
      'district': district,
      'ward': ward,
      'waterUsageNumber': waterUsageNumber,
      'newWaterUsageIndex': newWaterUsageIndex,
      'oldWaterUsageIndex': oldWaterUsageIndex,
      'startTime': startTime?.millisecondsSinceEpoch,
      'endTime': endTime?.millisecondsSinceEpoch,
      'status': status,
      'moneyNumber': moneyNumber,
      'debtMoneyNumber': debtMoneyNumber,
    };
  }

  factory Customer.fromJson(Map<String, dynamic> map) {
    return Customer(
      customerId: map['customerId'] as int,
      customerName:
          map['customerName'] != null ? map['customerName'] as String : null,
      customerPhone:
          map['customerPhone'] != null ? map['customerPhone'] as String : null,
      customerEmail:
          map['customerEmail'] != null ? map['customerEmail'] as String : null,
      provine: map['provine'] != null ? map['provine'] as String : null,
      district: map['district'] != null ? map['district'] as String : null,
      ward: map['ward'] != null ? map['ward'] as String : null,
      waterUsageNumber: map['waterUsageNumber'] != null
          ? map['waterUsageNumber'] as int
          : null,
      newWaterUsageIndex: map['newWaterUsageIndex'] != null
          ? map['newWaterUsageIndex'] as int
          : null,
      oldWaterUsageIndex: map['oldWaterUsageIndex'] != null
          ? map['oldWaterUsageIndex'] as int
          : null,
      startTime:
          map['startTime'] != null ? DateTime.parse(map['startTime']) : null,
      endTime: map['endTime'] != null ? DateTime.parse(map['endTime']) : null,
      status: map['status'] != null ? map['status'] as String : null,
      moneyNumber:
          map['moneyNumber'] != null ? map['moneyNumber'] as int : null,
      debtMoneyNumber:
          map['debtMoneyNumber'] != null ? map['debtMoneyNumber'] as int : null,
    );
  }
}
