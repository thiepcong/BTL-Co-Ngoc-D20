import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import '../repository/stat_repository.dart';
import 'stat_state.dart';

class StatCubit extends Cubit<StatState> {
  StatCubit(this._repo) : super(const StatState());

  final StatRepository _repo;

  void getDebtCustomerList() async {
    try {
      if (state.currentDistrict == null ||
          state.currentWard == null ||
          state.currentSelectDate == null) {
        emit(state.copyWith(
            message: "Vui lòng chọn quận/huyện, xã/phường và tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getDebtCustomerList(
        district: state.currentDistrict!,
        ward: state.currentWard!,
        date: state.currentSelectDate!,
        page: 1,
        size: 10,
      );
      emit(state.copyWith(
          isLoading: false, currentItem: res, currentDebt: null));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }

  void getDebtCustomer() async {
    try {
      if (state.currentDistrict == null ||
          state.currentWard == null ||
          state.currentSelectDate == null) {
        emit(state.copyWith(
            message: "Vui lòng chọn quận/huyện, xã/phường và tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getDebtCustomer(
        district: state.currentDistrict!,
        ward: state.currentWard!,
        date: state.currentSelectDate!,
      );
      emit(state.copyWith(isLoading: false, currentDebt: res));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }

  void getNewCustomers() async {
    try {
      if (state.currentDistrict == null ||
          state.currentWard == null ||
          state.currentSelectDate == null) {
        emit(state.copyWith(
            message: "Vui lòng chọn quận/huyện, xã/phường và tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getNewCustomers(
        district: state.currentDistrict!,
        ward: state.currentWard!,
        date: state.currentSelectDate!,
        page: 1,
        size: 10,
      );
      emit(state.copyWith(
          isLoading: false, currentItem: res, currentDebt: null));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      rethrow;
    }
  }

  void clean() {
    emit(state.copyWith(
      currentItem: null,
      currentDistrict: null,
      currentDebt: null,
      currentSelectDate: null,
    ));
  }

  void setCurrentDistrict(String value) {
    emit(state.copyWith(currentDistrict: value, currentWard: null));
  }

  void setCurrentSelectDate(DateTime value) {
    emit(state.copyWith(currentSelectDate: value));
  }

  void setCurrentWard(String value) {
    emit(state.copyWith(currentWard: value));
  }
}
