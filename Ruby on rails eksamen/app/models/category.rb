class Category < ApplicationRecord
  has_and_belongs_to_many :books
  validates_presence_of :name
  validates_uniqueness_of :name
  validates_length_of :name, :maximum => 25
end
