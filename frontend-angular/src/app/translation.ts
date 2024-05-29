export class Translation {
  id: String;
  translation: String;
  user: boolean;
  quantity: String;

  constructor(id: String, translation: String, isUser: boolean, quantity: String) {
    this.id = id;
    this.translation = translation;
    this.user = isUser;
    this.quantity = quantity;
  }
}
