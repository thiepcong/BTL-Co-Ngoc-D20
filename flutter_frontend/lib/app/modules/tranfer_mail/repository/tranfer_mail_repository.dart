import '../../../core/models/atachment.dart';
import '../../../core/models/report_info_request.dart';
import '../../../core/models/template_model.dart';
import '../../../core/models/tranfer_mail_response.dart';
import '../api/tranfer_mail_api.dart';

class TranferMailRepository {
  final TranferMailApi _api;

  TranferMailRepository(this._api);

  Future<TranferMailResponse> getAllTemplate({
    required int pageNum,
    required int pageSize,
  }) {
    return _api.getAllTemplate(pageNum: pageNum, pageSize: pageSize);
  }

  Future<bool> sendEmail({
    ReportInforRequest? reportInforRequest,
    // required List<Customer> customers,
    required TemplateModel template,
    required List<Atachment> attachment,
  }) {
    return _api.sendEmail(
      reportInforRequest: reportInforRequest,
      // customers: customers,
      template: template,
      attachment: attachment,
    );
  }
}
