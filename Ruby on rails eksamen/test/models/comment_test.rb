require 'test_helper'

class CommentTest < ActiveSupport::TestCase

  def setup
    @book = books(:one)

    @category = categories(:one)

    @user = users(:one)
  end

  test "comment can't be saved without content" do
    comment = Comment.new(user_id: @user.id, book_id: @book.id)
    assert comment.invalid?

    comment.content = "this is valid content"
    assert comment.valid?
  end

  test "comment can't be saved without a book" do
    comment = Comment.new(user_id: @user.id, content: "this is valid content")
    assert comment.invalid?

    comment.book_id = @book.id
    assert comment.valid?
  end

  test "comment can't be saved without a user" do
    comment = Comment.new(book_id: @book.id, content: "this is valid content")
    assert comment.invalid?

    comment.user_id = @user.id
    assert comment.valid?
  end

  test "comment is deleted when book is deleted" do
    newBook = @book

    comment = Comment.create(user_id: @user.id, book_id: newBook.id, content: "this is valid content")
    assert comment.valid?

    Book.destroy(newBook.id)

    assert_not Book.exists?(newBook.id)
    assert_not Comment.exists?(comment.id)
  end

  test "comment is not deleted when user is deleted" do
    user = User.create(name: "validName", password_digest: "validPassword123", email: "thisIs@valid.mail", user_profile: "admin")
    assert user.valid?

    comment = Comment.create(user_id: user.id, book_id: @book.id, content: "this is valid content")
    assert comment.valid?

    User.destroy(user.id)

    assert_not User.exists?(user.id)
    assert Comment.exists?(comment.id)
  end

  test "created_at is automaticly added when comment is created" do
    comment = Comment.new(user_id: @user.id, book_id: @book.id, content: "this is valid content")
    assert comment.valid?

    assert_nil comment.created_at
    assert_nil comment.updated_at

    comment = Comment.create(user_id: @user.id, book_id: @book.id, content: "this is valid content")

    assert_not_nil comment.created_at
    assert_not_nil comment.updated_at
  end
end
