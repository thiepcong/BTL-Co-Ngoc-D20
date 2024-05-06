import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_frontend/app/core/models/customer.dart';
import 'package:flutter_frontend/app/network/exceptions/bad_request_exception.dart';
import '../repository/stat_repository.dart';
import 'stat_state.dart';

class StatCubit extends Cubit<StatState> {
  StatCubit(this._repo) : super(const StatState());

  final StatRepository _repo;

  void setCurrentPage(int page, int type) {
    emit(state.copyWith(currentPage: page));
    if (type == 1) {
      getRevenueList();
    } else if (type == 2) {
      getNewCustomers();
    } else {
      getDebtCustomer();
    }
  }

  void getRevenueList() async {
    try {
      emit(state.copyWith(message: null));
      if (state.currentSelectDate == null) {
        emit(state.copyWith(message: "Vui lòng chọn tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getRevenueList(
        district: state.currentDistrict,
        ward: state.currentWard,
        date: state.currentSelectDate!,
        page: state.currentPage,
        size: 10,
      );
      emit(state.copyWith(isLoading: false, currentItem: res));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      if (e is StateError) return;
      rethrow;
    }
  }

  void getDebtCustomerList() async {
    try {
      emit(state.copyWith(message: null));
      if (state.currentSelectDate == null) {
        emit(state.copyWith(message: "Vui lòng chọn tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getDebtCustomerList(
        district: state.currentDistrict,
        ward: state.currentWard,
        date: state.currentSelectDate!,
        page: state.currentPage,
        size: 10,
      );
      emit(state.copyWith(
          isLoading: false, currentItem: res, currentDebt: null));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      if (e is StateError) return;
      rethrow;
    }
  }

  void getDebtCustomer() async {
    try {
      emit(state.copyWith(message: null));
      if (state.currentSelectDate == null) {
        emit(state.copyWith(message: "Vui lòng chọn tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getDebtCustomer(
        district: state.currentDistrict,
        ward: state.currentWard,
        date: state.currentSelectDate!,
      );
      emit(state.copyWith(isLoading: false, currentDebt: res));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      if (e is StateError) return;
      rethrow;
    }
  }

  void getNewCustomers() async {
    try {
      emit(state.copyWith(message: null));
      if (state.currentSelectDate == null) {
        emit(state.copyWith(message: "Vui lòng chọn tháng"));
        return;
      }
      emit(state.copyWith(isLoading: true, message: null));
      final res = await _repo.getNewCustomersList(
        district: state.currentDistrict,
        ward: state.currentWard,
        date: state.currentSelectDate!,
        page: state.currentPage,
        size: 10,
      );
      final res2 = await _repo.getNewCustomers(
        district: state.currentDistrict,
        ward: state.currentWard,
        date: state.currentSelectDate!,
        page: state.currentPage,
        size: 10,
      );
      emit(state.copyWith(
        isLoading: false,
        currentItem: res,
        currentNew: res2,
        currentDebt: null,
      ));
    } catch (e) {
      if (e is BadRequestException) {
        emit(state.copyWith(isLoading: false, message: e.message));
        return;
      }
      if (e is StateError) return;
      rethrow;
    }
  }

  void clean() {
    emit(state.copyWith(
      currentItem: null,
      currentDistrict: null,
      currentDebt: null,
      currentSelectDate: null,
      currentPage: 1,
      currentWard: null,
      currentNew: null,
      message: null,
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
}
