import { IAddress } from 'app/shared/model/address.model';
import { IProduct } from 'app/shared/model/product.model';
import { IHistory } from 'app/shared/model/history.model';

export interface IClient {
  id?: number;
  name?: string;
  addresses?: IAddress[];
  products?: IProduct[];
  histories?: IHistory[];
}

export const defaultValue: Readonly<IClient> = {};
