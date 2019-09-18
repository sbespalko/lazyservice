import { Moment } from 'moment';
import { IProductHistory } from 'app/shared/model/product-history.model';

export interface IProduct {
  id?: number;
  code?: string;
  name?: string;
  startDt?: Moment;
  endDt?: Moment;
  clientId?: number;
  histories?: IProductHistory[];
}

export const defaultValue: Readonly<IProduct> = {};
