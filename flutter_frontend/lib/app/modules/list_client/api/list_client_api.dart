import '../../../core/base/base_remote_source.dart';
import '../../../core/models/customer.dart';
import '../../../core/values/api_url_constant.dart';

class ListClientApi extends BaseRemoteSource {
  Future<List<Customer>> getListClient({
    required String district,
    required String ward,
    required int page,
    required int size,
  }) async {
    final request = dioClient.post(ApiUrlConstants.listClient, data: {
      "provine": "Hà Nội",
      "district": district,
      "ward": ward,
      "page": page,
      "size": size
    });
    try {
      return callApiWithErrorParser(request).then((value) =>
          (data['reportDTOList'] as List)
              .map((e) => Customer.fromJson(e))
              .toList());
    } catch (e) {
      rethrow;
    }
  }
}

final data = {
  "customerDTO": null,
  "reportDTO": null,
  "reportDTOList": [
    {
      "customerId": 1,
      "customerName": "customer1",
      "customerPhone": "0987654321",
      "customerEmail": "customer1@gmail.com",
      "provine": "Ha Noi",
      "district": "Ha Dong",
      "ward": "Van Quan",
      "waterUsageNumber": 100,
      "newWaterUsageIndex": null,
      "oldWaterUsageIndex": null,
      "startTime": "2024-02-29T17:00:00.000+00:00",
      "endTime": "2024-03-31T16:59:59.000+00:00",
      "status": "paid"
    },
    {
      "customerId": 1,
      "customerName": "customer1",
      "customerPhone": "0987654321",
      "customerEmail": "customer1@gmail.com",
      "provine": "Ha Noi",
      "district": "Ha Dong",
      "ward": "Van Quan",
      "waterUsageNumber": 50,
      "newWaterUsageIndex": null,
      "oldWaterUsageIndex": null,
      "startTime": "2024-02-29T17:00:00.000+00:00",
      "endTime": "2024-03-24T08:51:32.000+00:00",
      "status": "unpaid"
    },
    {
      "customerId": 2,
      "customerName": "customer2",
      "customerPhone": "098765412",
      "customerEmail": "customer2@gmail.com",
      "provine": "Ha Noi",
      "district": "Ha Dong",
      "ward": "Van Quan",
      "waterUsageNumber": 50,
      "newWaterUsageIndex": null,
      "oldWaterUsageIndex": null,
      "startTime": null,
      "endTime": null,
      "status": "paid"
    },
    {
      "customerId": 3,
      "customerName": "customer3",
      "customerPhone": "096364235",
      "customerEmail": "customer3@gmail.com",
      "provine": "Ha Noi",
      "district": "Ha Dong",
      "ward": "Van Quan",
      "waterUsageNumber": 45,
      "newWaterUsageIndex": null,
      "oldWaterUsageIndex": null,
      "startTime": null,
      "endTime": null,
      "status": "unpaid"
    }
  ],
  "pageDto": {
    "last": true,
    "totalElements": 4,
    "totalPages": 1,
    "size": 10,
    "number": 1,
    "sort": {"empty": true, "sorted": false, "unsorted": true},
    "numberOfElements": 4,
    "first": true
  }
};
