import '../../../core/models/atachment.dart';
import '../../../core/models/customer.dart';
import '../../../core/models/template_model.dart';
import '../api/tranfer_mail_api.dart';

class TranferMailRepository {
  final TranferMailApi _api;

  TranferMailRepository(this._api);

  Future<List<TemplateModel>> getAllTemplate({
    required int pageNum,
    required int pageSize,
  }) {
    return _api.getAllTemplate(pageNum: pageNum, pageSize: pageSize);
  }

  Future<bool> sendEmail({
    required List<Customer> customers,
    required TemplateModel template,
    required List<Atachment> attachment,
  }) {
    return _api.sendEmail(
      customers: customers,
      template: template,
      attachment: attachment,
    );
  }
}
