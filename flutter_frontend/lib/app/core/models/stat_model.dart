import 'customer.dart';
import 'page.dart';

class StatModel {
  final List<Customer> reportDTOList;
  final Page pageDto;

  StatModel({required this.reportDTOList, required this.pageDto});

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'reportDTOList': reportDTOList.map((x) => x.toJson()).toList(),
      'pageDto': pageDto.toJson(),
    };
  }

  factory StatModel.fromJson(Map<String, dynamic> map) {
    return StatModel(
      reportDTOList: List<Customer>.from(
        (map['reportDTOList'] as List).map<Customer>(
          (x) => Customer.fromJson(x as Map<String, dynamic>),
        ),
      ),
      pageDto: Page.fromJson(map['pageDto'] as Map<String, dynamic>),
    );
  }
}
