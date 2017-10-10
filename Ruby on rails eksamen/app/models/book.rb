class Book < ApplicationRecord
  has_and_belongs_to_many :categories
  has_many :ratings, dependent: :destroy
  has_many :comments, dependent: :destroy
  has_many :for_sales, dependent: :destroy
  mount_uploader :image, BookUploader
  validates_presence_of :title, :description, :categories
  validates_uniqueness_of :title
  validates_length_of :title, :maximum => 80
  validates_length_of :description, :maximum => 25000



  def self.search(search)
    if search
      where('title iLIKE ?', "%#{search}%")
    else
      where(nil)
    end
  end

  def self.searchcategories(search)
    if search != nil
      category = Category.find(search)
      books = category.books
      return books
    else
      where(nil)
    end
  end

end
