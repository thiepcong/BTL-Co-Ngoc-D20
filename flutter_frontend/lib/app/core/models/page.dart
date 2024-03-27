class Page {
  final int totalElements;
  final int totalPages;
  final int size;
  final int number;

  Page({
    required this.totalElements,
    required this.totalPages,
    required this.size,
    required this.number,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'totalElements': totalElements,
      'totalPages': totalPages,
      'size': size,
      'number': number,
    };
  }

  factory Page.fromJson(Map<String, dynamic> map) {
    return Page(
      totalElements: map['totalElements'] as int,
      totalPages: map['totalPages'] as int,
      size: map['size'] as int,
      number: map['number'] as int,
    );
  }
}
