class NewCustomer {
  final int newCustomerNum;
  final int allCustomerNum;
  final String percent;

  NewCustomer({
    required this.newCustomerNum,
    required this.allCustomerNum,
    required this.percent,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'newCustomerNum': newCustomerNum,
      'allCustomerNum': allCustomerNum,
      'percent': percent,
    };
  }

  factory NewCustomer.fromJson(Map<String, dynamic> map) {
    return NewCustomer(
      newCustomerNum: map['newCustomerNum'] as int,
      allCustomerNum: map['allCustomerNum'] as int,
      percent: map['percent'] as String,
    );
  }
}
