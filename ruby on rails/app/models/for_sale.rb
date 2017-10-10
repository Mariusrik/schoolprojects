class ForSale < ApplicationRecord
  belongs_to :user
  belongs_to :book
  validates_presence_of :price, :contact
  validates_numericality_of :price, :greater_than_or_equal_to => 0, :less_than_or_equal_to => 999999
  validates_length_of :contact, :maximum => 255
end
