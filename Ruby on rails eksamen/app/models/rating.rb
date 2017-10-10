class Rating < ApplicationRecord
  belongs_to :book
  belongs_to :user
  validates_presence_of :score, :user_id, :book_id
  validates_numericality_of :score, :greater_than_or_equal_to => 0, :less_than_or_equal_to => 6
  validates_uniqueness_of :book_id, :scope => :user_id
end
