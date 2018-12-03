import { Moment } from 'moment';
import { ICmRequest } from 'app/shared/model//cm-request.model';

export interface ICmError {
    id?: number;
    errorId?: number;
    errorComponent?: number;
    errorCode?: string;
    errorDescription?: string;
    errorStackTrace?: string;
    errornDateTime?: Moment;
    request?: ICmRequest;
}

export class CmError implements ICmError {
    constructor(
        public id?: number,
        public errorId?: number,
        public errorComponent?: number,
        public errorCode?: string,
        public errorDescription?: string,
        public errorStackTrace?: string,
        public errornDateTime?: Moment,
        public request?: ICmRequest
    ) {}
}
