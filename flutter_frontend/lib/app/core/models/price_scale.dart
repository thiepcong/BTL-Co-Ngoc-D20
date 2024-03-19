// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';

class PriceScale {
  final int id;
  final int startIndex;
  final int endIndex;
  final double price;
  final String description;

  PriceScale({
    required this.id,
    required this.startIndex,
    required this.endIndex,
    required this.price,
    required this.description,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'startIndex': startIndex,
      'endIndex': endIndex,
      'price': price,
      'description': description,
    };
  }

  factory PriceScale.fromMap(Map<String, dynamic> map) {
    return PriceScale(
      id: map['id'] as int,
      startIndex: map['startIndex'] as int,
      endIndex: map['endIndex'] as int,
      price: map['price'] as double,
      description: map['description'] as String,
    );
  }

  String toJson() => json.encode(toMap());

  factory PriceScale.fromJson(String source) =>
      PriceScale.fromMap(json.decode(source) as Map<String, dynamic>);
}
