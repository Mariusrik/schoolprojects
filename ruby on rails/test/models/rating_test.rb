require 'test_helper'

class RatingTest < ActiveSupport::TestCase

  def setup
    @book = books(:one)
    @book_two = books(:two)

    @category = categories(:one)

    @user = users(:one)
  end

  test "rating can't be saved without score" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    rating = Rating.new(user_id: user.id, book_id: @book.id)
    assert rating.invalid?

    rating.score = 5
    assert rating.valid?
  end

  test "rating can't be saved without a book" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    rating = Rating.new(user_id: user.id, score: 5)
    assert rating.invalid?

    rating.book_id = @book.id
    assert rating.valid?
  end

  test "rating can't be saved without a user" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    rating = Rating.new(book_id: @book.id, score: 5)
    assert rating.invalid?

    rating.user_id = user.id
    assert rating.valid?
  end

  test "rating is deleted when book is deleted" do
    newBook = @book
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail", user_profile: "admin")

    rating = Rating.new(user_id: user.id, book_id: newBook.id, score: 5)
    assert rating.valid?

    Book.destroy(newBook.id)

    assert_not Book.exists?(newBook.id)
    assert_not Rating.exists?(rating.id)
  end

  test "rating is not deleted when user is deleted" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail")
    assert user.valid?

    rating = Rating.create(user_id: user.id, book_id: @book_two.id, score: 5)
    assert rating.valid?

    User.destroy(user.id)

    assert_not User.exists?(user.id)
    assert Rating.exists?(rating.id)
  end

  test "created_at is automaticly added when rating is created" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIsAnother@valid.mail")
    rating = Rating.new(user_id: user.id, book_id: @book_two.id, score: 5)
    assert rating.valid?

    assert_nil rating.created_at
    assert_nil rating.updated_at

    rating = Rating.create(user_id: user.id, book_id: @book_two.id, score: 5)

    assert_not_nil rating.created_at
    assert_not_nil rating.updated_at
  end
end
