extension IntegerX on int {
  String intToAnswer() {
    switch (this) {
      case 1:
        return 'A';
      case 2:
        return 'B';
      case 3:
        return 'C';
      default:
        return 'D';
    }
  }

  String intToNdh() {
    switch (this) {
      case 1:
        return "Doraemon";
      case 2:
        return "Mickey Mouse";
      case 3:
        return "Bill Gates";
      case 4:
        return "Homer Simpson";
      case 5:
        return "Stephen Hawking";
      default:
        return "Elon Musk";
    }
  }

  String intToTimeType() {
    switch (this) {
      case 0:
        return 'day';
      case 1:
        return 'month';
      default:
        return 'year';
    }
  }
}
