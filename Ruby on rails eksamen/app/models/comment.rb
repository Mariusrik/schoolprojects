class Comment < ApplicationRecord
  belongs_to :user
  belongs_to :book
  validates_presence_of :content
  validates_length_of :content, :maximum => 1500
end
