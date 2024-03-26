class Page {
  final int totalElements;
  final int totalPages;

  Page({required this.totalElements, required this.totalPages});

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'totalElements': totalElements,
      'totalPages': totalPages,
    };
  }

  factory Page.fromJson(Map<String, dynamic> map) {
    return Page(
      totalElements: map['totalElements'] as int,
      totalPages: map['totalPages'] as int,
    );
  }
}
