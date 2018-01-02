import { BaseEntity } from './../../shared';

export class VoteResult implements BaseEntity {
    constructor(
        public id?: number,
        public voteTime?: any,
        public openid?: string,
        public voteItem?: BaseEntity,
    ) {
    }
}
