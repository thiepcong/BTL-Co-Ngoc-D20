import 'package:intl/intl.dart';

import 'price_scale.dart';

class PriceList {
  final int? id;
  final DateTime applyDate;
  final int? status;
  final UserType userType;
  final List<PriceScale> listPriceScales;

  PriceList({
    this.id,
    required this.applyDate,
    this.status,
    required this.userType,
    required this.listPriceScales,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'applyDate': DateFormat('yyyy-MM-dd').format(applyDate),
      'listPriceScales': listPriceScales.map((x) => x.toJson()).toList(),
    };
  }

  factory PriceList.fromJson(Map<String, dynamic> map) {
    return PriceList(
      id: map['id'] as int,
      applyDate: DateTime.parse(map['applyDate']),
      status: map['status'] as int,
      userType: UserType.fromJson(map['userType'] as Map<String, dynamic>),
      listPriceScales: List<PriceScale>.from(
        (map['listPriceScales'] as List).map<PriceScale>(
          (x) => PriceScale.fromJson(x as Map<String, dynamic>),
        ),
      ),
    );
  }

  PriceList copyWith({
    int? id,
    DateTime? applyDate,
    int? status,
    UserType? userType,
    List<PriceScale>? listPriceScales,
  }) {
    return PriceList(
      id: id ?? this.id,
      applyDate: applyDate ?? this.applyDate,
      status: status ?? this.status,
      userType: userType ?? this.userType,
      listPriceScales: listPriceScales ?? this.listPriceScales,
    );
  }
}

class UserType {
  final int id;
  final String typeName;

  UserType({required this.id, required this.typeName});

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'id': id,
      'typeName': typeName,
    };
  }

  factory UserType.fromJson(Map<String, dynamic> map) {
    return UserType(
      id: map['id'] as int,
      typeName: map['typeName'] as String,
    );
  }
}
