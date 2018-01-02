import { BaseEntity } from './../../shared';

export class Vote implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public startDate?: any,
        public endDate?: any,
    ) {
    }
}
