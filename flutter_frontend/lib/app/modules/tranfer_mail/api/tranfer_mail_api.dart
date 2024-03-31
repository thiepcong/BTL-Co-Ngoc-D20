import '../../../core/base/base_remote_source.dart';
import '../../../core/models/atachment.dart';
import '../../../core/models/customer.dart';
import '../../../core/models/template_model.dart';
import '../../../core/values/api_url_constant.dart';

class TranferMailApi extends BaseRemoteSource {
  Future<List<TemplateModel>> getAllTemplate(
      {required int pageNum, required int pageSize}) async {
    final request =
        dioClient.get(ApiUrlConstants.tranferMail(pageNum, pageSize));
    try {
      return callApiWithErrorParser(request).then((value) =>
          (value.data["data"] as List)
              .map((e) => TemplateModel.fromJson(e))
              .toList());
    } catch (e) {
      rethrow;
    }
  }

  Future<bool> sendEmail({
    required List<Customer> customers,
    required TemplateModel template,
    required List<Atachment> attachment,
  }) async {
    final request = dioClient.post(
      ApiUrlConstants.sendMail,
      data: {
        "toList": customers.map((e) => e.customerEmail).toList(),
        "template": template.toJson(),
        "attachments": attachment.map((x) => x.toJson()).toList(),
      },
    );
    try {
      return callApiWithErrorParser(request).then((value) => true);
    } catch (e) {
      rethrow;
    }
  }
}
