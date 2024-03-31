import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import '../../../core/models/customer.dart';
import '../repository/tranfer_mail_repository.dart';
import 'tranfer_mail_state.dart';

class TranferMailCubit extends Cubit<TranferMailState> {
  TranferMailCubit(this._repo) : super(const TranferMailState());

  final TranferMailRepository _repo;

  void init() async {
    try {
      emit(state.copyWith(isLoading: true));
      final res = await _repo.getAllTemplate(pageNum: 1, pageSize: 10);
      emit(state.copyWith(isLoading: false, templates: res));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }

  void setSelectedIndex(int selectedIndex) {
    emit(state.copyWith(selectedIndex: selectedIndex));
  }

  void sendEmail(List<Customer> customers) async {
    try {
      emit(state.copyWith(isLoading: true, sendEmailDone: false));
      final res = await _repo.sendEmail(
        customers: customers,
        template: state.templates[state.selectedIndex],
        attachment: [],
      );
      emit(state.copyWith(isLoading: false, sendEmailDone: res));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }
}
