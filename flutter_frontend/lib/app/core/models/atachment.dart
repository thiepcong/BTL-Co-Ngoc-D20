class Atachment {
  final int id;
  final String fileName;
  final String originalFileName;

  Atachment({
    required this.id,
    required this.fileName,
    required this.originalFileName,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'id': id,
      'fileName': fileName,
      'originalFileName': originalFileName,
    };
  }

  factory Atachment.fromJson(Map<String, dynamic> map) {
    return Atachment(
      id: map['id'] as int,
      fileName: map['fileName'] as String,
      originalFileName: map['originalFileName'] as String,
    );
  }
}
