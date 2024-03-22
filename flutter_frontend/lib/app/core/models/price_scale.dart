// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'dart:convert';

class PriceScale {
  final int? id;
  final int startIndex;
  final int endIndex;
  final int price;
  final String? description;

  PriceScale({
    this.id,
    required this.startIndex,
    required this.endIndex,
    required this.price,
    this.description,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
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
      price: map['price'] as int,
      description:
          map['description'] != null ? map['description'] as String : null,
    );
  }

  String toJson() => json.encode(toMap());

  factory PriceScale.fromJson(String source) =>
      PriceScale.fromMap(json.decode(source) as Map<String, dynamic>);

  PriceScale copyWith({
    int? id,
    int? startIndex,
    int? endIndex,
    int? price,
    String? description,
  }) {
    return PriceScale(
      id: id ?? this.id,
      startIndex: startIndex ?? this.startIndex,
      endIndex: endIndex ?? this.endIndex,
      price: price ?? this.price,
      description: description ?? this.description,
    );
  }
}
