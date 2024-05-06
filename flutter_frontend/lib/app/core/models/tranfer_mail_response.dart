import 'template_model.dart';

class TranferMailResponse {
  final int currentPage;
  final int totalPages;
  final int totalItems;
  final List<TemplateModel> data;

  TranferMailResponse(
      {required this.currentPage,
      required this.totalPages,
      required this.totalItems,
      required this.data});

  factory TranferMailResponse.fromJson(Map<String, dynamic> map) {
    return TranferMailResponse(
      currentPage: map['currentPage'] as int,
      totalPages: map['totalPages'] as int,
      totalItems: map['totalItems'] as int,
      data: List<TemplateModel>.from(
        (map['data'] as List).map<TemplateModel>(
          (x) => TemplateModel.fromJson(x as Map<String, dynamic>),
        ),
      ),
    );
  }
}
