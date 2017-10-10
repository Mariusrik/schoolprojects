require 'test_helper'

class CategoryTest < ActiveSupport::TestCase

  test "category must not have empty name" do
    category = Category.new

    assert category.invalid?
    assert category.errors[:name].any?
  end

  test "categories must be unique" do
    category = Category.create(name: "myCategory")
    assert category.valid?

    thirdCategory = Category.create(name: "myCategory")
    assert_not thirdCategory.valid?
  end
end
