import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import '../../../core/models/customer.dart';
import '../repository/list_client_repository.dart';
import 'list_client_state.dart';

class ListClientCubit extends Cubit<ListClientState> {
  ListClientCubit(this._repo) : super(const ListClientState());

  final ListClientRepository _repo;

  void setCurrentDistrict(String value) {
    emit(state.copyWith(currentDistrict: value, currentWard: null));
  }

  void setCurrentWard(String value) {
    emit(state.copyWith(currentWard: value));
  }

  void setCurrentPage(int page) {
    emit(state.copyWith(currentPage: page));
    getListClient();
  }

  void setCurrentFilter(String? value) {
    emit(state.copyWith(currentFilter: value));
  }

  void getListClient() async {
    if (state.currentDistrict == null || state.currentWard == null) {
      emit(state.copyWith(message: "Vui lòng chọn quận/huyện và xã/phường"));
      return;
    }
    emit(state.copyWith(isLoading: true, message: null));
    try {
      final res = await _repo.getListClient(
        district: state.currentDistrict!,
        ward: state.currentWard!,
        page: state.currentPage,
        size: 10,
        date: state.currentSelectDate,
        type: handle(),
      );
      emit(state.copyWith(currentItem: res, isLoading: false));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }

  void setCurrentTrandMail(Customer item, bool? isChoose) {
    if (isChoose == true) {
      List<Customer> li = List.from(state.customerMails);
      li.add(item);
      emit(state.copyWith(customerMails: li));
    } else if (isChoose == false) {
      List<Customer> li = List.from(state.customerMails);
      li.remove(item);
      emit(state.copyWith(customerMails: li));
    }
  }

  void setCurrentSelectDate(DateTime value) {
    emit(state.copyWith(currentSelectDate: value));
  }

  bool? handle() {
    if (state.currentFilter == null) return null;
    if (state.currentFilter == 'Chưa đóng tiền') return false;
    return true;
  }
}
