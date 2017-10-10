class CreateForSales < ActiveRecord::Migration[5.1]
  def change
    create_table :for_sales do |t|
      t.decimal :price
      t.string :contact
      t.references :user, foreign_key: true
      t.references :book, foreign_key: true

      t.timestamps
    end
  end
end
