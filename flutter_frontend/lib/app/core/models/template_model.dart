class TemplateModel {
  final int id;
  final String templateName;
  final String templateSubject;
  final String templateContent;
  final String createdDate;
  // final List<TemplateProperty> listProperties;

  TemplateModel({
    required this.id,
    required this.templateName,
    required this.templateSubject,
    required this.templateContent,
    required this.createdDate,
    // required this.listProperties,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'id': id,
      'templateName': templateName,
      'templateSubject': templateSubject,
      'templateContent': templateContent,
      'createdDate': createdDate,
      // 'listProperties': listProperties.map((x) => x.toMap()).toList(),
    };
  }

  factory TemplateModel.fromJson(Map<String, dynamic> map) {
    return TemplateModel(
      id: map['id'] as int,
      templateName: map['templateName'] as String,
      templateSubject: map['templateSubject'] as String,
      templateContent: map['templateContent'] as String,
      createdDate: map['createdDate'] as String,
      // listProperties: List<TemplateProperty>.from(
      //   (map['listProperties'] as List).map<TemplateProperty>(
      //     (x) => TemplateProperty.fromMap(x as Map<String, dynamic>),
      //   ),
      // ),
    );
  }
}

class TemplateProperty {
  final int id;
  final String description;
  final String propertyKey;

  TemplateProperty({
    required this.id,
    required this.description,
    required this.propertyKey,
  });

  Map<String, dynamic> toMap() {
    return <String, dynamic>{
      'id': id,
      'description': description,
      'propertyKey': propertyKey,
    };
  }

  factory TemplateProperty.fromMap(Map<String, dynamic> map) {
    return TemplateProperty(
      id: map['id'] as int,
      description: map['description'] as String,
      propertyKey: map['propertyKey'] as String,
    );
  }
}
