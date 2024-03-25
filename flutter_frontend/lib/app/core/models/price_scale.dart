class PriceScale {
  final int? id;
  final int startIndex;
  final int endIndex;
  final int price;

  PriceScale({
    this.id,
    required this.startIndex,
    required this.endIndex,
    required this.price,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'startIndex': startIndex,
      'endIndex': endIndex,
      'price': price,
    };
  }

  factory PriceScale.fromJson(Map<String, dynamic> map) {
    return PriceScale(
      id: map['id'] as int,
      startIndex: map['startIndex'] as int,
      endIndex: map['endIndex'] as int,
      price: map['price'] as int,
    );
  }

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
    );
  }
}
