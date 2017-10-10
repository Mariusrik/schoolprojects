require 'test_helper'

class BooksControllerTest < ActionDispatch::IntegrationTest
  setup do
    @book = books(:one)
  end

  test "should get new" do
    get new_book_url
    assert_response :found
  end

  test "should show book" do
    get book_url(@book)
    assert_response :success
  end

end
