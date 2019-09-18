import { Moment } from 'moment';
import { OperationType } from 'app/shared/model/enumerations/operation-type.model';

export interface IProductHistory {
  id?: number;
  operationDt?: Moment;
  opertationType?: OperationType;
  sum?: number;
  productId?: number;
}

export const defaultValue: Readonly<IProductHistory> = {};
