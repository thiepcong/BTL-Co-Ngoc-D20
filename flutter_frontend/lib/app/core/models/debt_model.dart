class DebtModel {
  final int debtNum;
  final int allCustomerNum;
  final String percent;

  DebtModel({
    required this.debtNum,
    required this.allCustomerNum,
    required this.percent,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'debtNum': debtNum,
      'allCustomerNum': allCustomerNum,
      'percent': percent,
    };
  }

  factory DebtModel.fromJson(Map<String, dynamic> map) {
    return DebtModel(
      debtNum: map['debtNum'] as int,
      allCustomerNum: map['allCustomerNum'] as int,
      percent: map['percent'] as String,
    );
  }
}
