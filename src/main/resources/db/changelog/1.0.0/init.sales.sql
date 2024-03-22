create table if not exists sales
(
    id         uuid primary key,
    products   jsonb not null,
    cost_total numeric default 0,
    type       varchar
);

comment on table sales is 'Продажи';
comment on column sales.products is 'Проданные товары';
comment on column sales.cost_total is 'Общая сумма продажи';
comment on column sales.type is 'Тип продажи';