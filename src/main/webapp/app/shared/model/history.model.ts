import { Moment } from 'moment';

export interface IHistory {
  id?: number;
  startDt?: Moment;
  endDt?: Moment;
  riskPower?: number;
  clientId?: number;
}

export const defaultValue: Readonly<IHistory> = {};
