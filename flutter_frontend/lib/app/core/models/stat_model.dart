// ignore_for_file: public_member_api_docs, sort_constructors_first
import 'customer.dart';
import 'page.dart';

class StatModel {
  final List<Customer> reportDTOList;
  final Page pageDto;
  final int? totalMoney;
  final int? totalDebtNumber;

  StatModel({
    required this.reportDTOList,
    required this.pageDto,
    this.totalMoney,
    this.totalDebtNumber,
  });

  Map<String, dynamic> toJson() {
    return <String, dynamic>{
      'reportDTOList': reportDTOList.map((x) => x.toJson()).toList(),
      'pageDto': pageDto.toJson(),
      'totalMoney': totalMoney,
      'totalDebtNumber': totalDebtNumber,
    };
  }

  factory StatModel.fromJson(Map<String, dynamic> map, {String? keyList}) {
    return StatModel(
      reportDTOList: List<Customer>.from(
        (map[keyList ?? 'reportDTOList'] as List).map<Customer>(
          (x) => Customer.fromJson(x as Map<String, dynamic>),
        ),
      ),
      pageDto: Page.fromJson(map['pageDto'] as Map<String, dynamic>),
      totalMoney: map['totalMoney'] != null ? map['totalMoney'] as int : null,
      totalDebtNumber:
          map['totalDebtNumber'] != null ? map['totalDebtNumber'] as int : null,
    );
  }

  StatModel copyWith({
    List<Customer>? reportDTOList,
    Page? pageDto,
    int? totalMoney,
    int? totalDebtNumber,
  }) {
    return StatModel(
      reportDTOList: reportDTOList ?? this.reportDTOList,
      pageDto: pageDto ?? this.pageDto,
      totalMoney: totalMoney ?? this.totalMoney,
      totalDebtNumber: totalDebtNumber ?? this.totalDebtNumber,
    );
  }
}
