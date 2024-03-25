// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';

import 'price_scale.dart';

class PriceList {
  final int id;
  final DateTime applyDate;
  final int status;
  final UserType userType;
  final List<PriceScale> listPriceScales;

  PriceList({
    required this.id,
    required this.applyDate,
    required this.status,
    required this.userType,
    required this.listPriceScales,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'applyDate': applyDate.toString(),
      'listPriceScales': listPriceScales.map((x) => x.toMap()).toList(),
    };
  }

  factory PriceList.fromMap(Map<String, dynamic> map) {
    return PriceList(
      id: map['id'] as int,
      applyDate: DateTime.parse(map['applyDate']),
      status: map['status'] as int,
      userType: UserType.fromMap(map['userType'] as Map<String, dynamic>),
      listPriceScales: List<PriceScale>.from(
        (map['listPriceScales'] as List<int>).map<PriceScale>(
          (x) => PriceScale.fromMap(x as Map<String, dynamic>),
        ),
      ),
    );
  }

  String toJson() => json.encode(toMap());

  factory PriceList.fromJson(String source) =>
      PriceList.fromMap(json.decode(source) as Map<String, dynamic>);

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

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'typeName': typeName,
    };
  }

  factory UserType.fromMap(Map<String, dynamic> map) {
    return UserType(
      id: map['id'] as int,
      typeName: map['typeName'] as String,
    );
  }

  String toJson() => json.encode(toMap());

  factory UserType.fromJson(String source) =>
      UserType.fromMap(json.decode(source) as Map<String, dynamic>);
}
