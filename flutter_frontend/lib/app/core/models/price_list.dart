// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';

import 'price_scale.dart';

class PriceList {
  final int id;
  final DateTime applyDate;
  final String description;
  final String type;
  final List<PriceScale> items;

  PriceList({
    required this.id,
    required this.applyDate,
    required this.description,
    required this.type,
    required this.items,
  });

  PriceList copyWith({
    int? id,
    DateTime? applyDate,
    String? description,
    String? type,
    List<PriceScale>? items,
  }) {
    return PriceList(
      id: id ?? this.id,
      applyDate: applyDate ?? this.applyDate,
      description: description ?? this.description,
      type: type ?? this.type,
      items: items ?? this.items,
    );
  }

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'applyDate': applyDate.millisecondsSinceEpoch,
      'description': description,
      'type': type,
      'items': items.map((x) => x.toMap()).toList(),
    };
  }

  factory PriceList.fromMap(Map<String, dynamic> map) {
    return PriceList(
      id: map['id'] as int,
      applyDate: DateTime.fromMillisecondsSinceEpoch(map['applyDate'] as int),
      description: map['description'] as String,
      type: map['type'] as String,
      items: List<PriceScale>.from((map['items'] as List<int>).map<PriceScale>((x) => PriceScale.fromMap(x as Map<String,dynamic>),),),
    );
  }

  String toJson() => json.encode(toMap());

  factory PriceList.fromJson(String source) => PriceList.fromMap(json.decode(source) as Map<String, dynamic>);
}
