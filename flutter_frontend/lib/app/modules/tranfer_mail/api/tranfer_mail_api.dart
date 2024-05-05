import '../../../core/base/base_remote_source.dart';
import '../../../core/models/atachment.dart';
import '../../../core/models/report_info_request.dart';
import '../../../core/models/template_model.dart';
import '../../../core/models/tranfer_mail_response.dart';
import '../../../core/values/api_url_constant.dart';

class TranferMailApi extends BaseRemoteSource {
  Future<TranferMailResponse> getAllTemplate(
      {required int pageNum, required int pageSize}) async {
    final request =
        dioClient.get(ApiUrlConstants.tranferMail(pageNum, pageSize));
    try {
      return callApiWithErrorParser(request)
          .then((value) => TranferMailResponse.fromJson(value.data));
    } catch (e) {
      rethrow;
    }
  }

  Future<bool> sendEmail({
    ReportInforRequest? reportInforRequest,
    // required List<Customer> customers,
    required TemplateModel template,
    required List<Atachment> attachment,
  }) async {
    final request = dioClient.post(
      ApiUrlConstants.sendMail,
      data: {
        "reportInforRequest": reportInforRequest?.toJson(),
        // "toList": customers.map((e) => e.customerEmail).toList(),
        "templateId": template.id,
        "attachmentIds": attachment.map((x) => x.id).toList(),
      },
    );
    try {
      return callApiWithErrorParser(request).then((value) => true);
    } catch (e) {
      rethrow;
    }
  }
}
